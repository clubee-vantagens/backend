package com.clubeevantagens.authmicroservice.service;

import com.clubeevantagens.authmicroservice.model.User;
import com.clubeevantagens.authmicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    // ENVIAR "TOKEN" PARA EMAIL
    public ResponseEntity<?> sendPasswordResetTokenToEmail(String email)  {

        Optional<User> userOptional = userRepository.findUserByEmail(email);

        if (userOptional.isPresent()) {// email existe
            String token = UUID.randomUUID().toString();// gera token
            User u = userOptional.get();
            u.setResetPasswordToken(token);
            u.setResetPasswordExpiryDate(u.calculateExpiryDate(2880));

            userRepository.save(u);// salva "token" em "User"

            String resp = emailService.sendSimpleEmail(email, "Redefinição de Senha",
                    "Para resetar sua senha use o codigo abaixo:\n" + token + "\n\n\n" +
                            "Se você não fez uma solicitação, pode ignorar este e-mail.\n\n" +
                            "IMPORTANTE: Esse link possui validade de 48 horas, após esse prazo, solicite uma nova redefinição de senha repetindo o processo realizado.");

            return ResponseEntity.ok().body(resp);
        }else{// email não existe
            return ResponseEntity.notFound().build();
        }


    }


    // ENVIAR "TOKEN" E "PASSWORD" PARA VALIDAÇÂO
    public ResponseEntity<?> resetPassword(String token, String newPassword) {

        Optional<User> userOptional = userRepository.findByResetPasswordToken(token);


        if(userOptional.isPresent() ){// token existe

            User u = userOptional.get();
            if(!u.isExpired()){// token valido
                u.setPassword(newPassword);
                u.setResetPasswordExpiryDate(null);
                u.setResetPasswordToken(null);

                userRepository.save(u);

                return ResponseEntity.ok().body("Senha alterada com sucesso");
            }else{// token invalido
                u.setResetPasswordExpiryDate(null);
                u.setResetPasswordToken(null);

                userRepository.save(u);

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("token invalido");
            }


        }else{// token não existe
            return ResponseEntity.notFound().build();
        }

    }
}