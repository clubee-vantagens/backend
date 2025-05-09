package com.clubeevantagens.authmicroservice.app.usecase;

import com.clubeevantagens.authmicroservice.infra.exception.general.EntityNotFoundException;
import com.clubeevantagens.authmicroservice.infra.exception.general.UniqueValueInUse;
import com.clubeevantagens.authmicroservice.domain.entity.Category;
import com.clubeevantagens.authmicroservice.domain.entity.Client;
import com.clubeevantagens.authmicroservice.domain.entity.User;
import com.clubeevantagens.authmicroservice.app.dto.request.ClientRequestDto;
import com.clubeevantagens.authmicroservice.app.dto.request.ClientUpdateRequestDto;
import com.clubeevantagens.authmicroservice.app.dto.response.ClientResponseDto;
import com.clubeevantagens.authmicroservice.domain.enums.CategoryType;
import com.clubeevantagens.authmicroservice.domain.enums.Role;
import com.clubeevantagens.authmicroservice.app.repository.CategoryRepository;
import com.clubeevantagens.authmicroservice.app.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final ViaCepService viaCepService;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;

    public ClientService(ClientRepository clientRepository, ViaCepService viaCepService, PasswordEncoder passwordEncoder, CategoryRepository categoryRepository) {
        this.clientRepository = clientRepository;
        this.viaCepService = viaCepService;
        this.passwordEncoder = passwordEncoder;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public ClientResponseDto register(ClientRequestDto dto) {
        if (clientRepository.existsByUserEmail(dto.email())) {
            throw new UniqueValueInUse(dto.email());
        }

        if(clientRepository.existsByCpf(dto.cpf())) {
            throw new UniqueValueInUse(dto.cpf());
        }

        Client client = new Client(dto);
        client.setUser(new User(dto.email(), passwordEncoder.encode(dto.password())));
        client.getUser().setActive(true);
        client.getUser().setRole(Role.CLIENT);

        client.getAddress().addFromViaCepRequest(viaCepService.requestToViaCep(dto.cep()));
        client.getAddress().setAddressNumber(dto.addressNumber());
        client.getAddress().setComplement(dto.complement());
        client.getAddress().setContactPhone(dto.phoneNumber());
        client.setPreferences(processCategories(dto.preferences()));

        clientRepository.save(client);

        return new ClientResponseDto(client);
    }

    public List<ClientResponseDto> getAll() {
        Collection<Client> clients = clientRepository.findAll();

        List<ClientResponseDto> dtoList = new ArrayList<>();
        for (Client client : clients) {
            dtoList.add(new ClientResponseDto(client));
        }

        return dtoList;
    }

    public ClientResponseDto getByEmail(String email) {
        Optional<Client> client = clientRepository.findByUserEmail(email);
        if(client.isEmpty()){
            throw new EntityNotFoundException("Client not found!");
        }

        return new ClientResponseDto(client.get());
    }

    public ClientResponseDto getById(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if(client.isEmpty()){
            throw new EntityNotFoundException("Client not found!");
        }
        return new ClientResponseDto(client.get());
    }

    @Transactional
    public ClientResponseDto update(Long id, ClientUpdateRequestDto dto) {
        Optional<Client> client = clientRepository.findById(id);

        if(client.isEmpty()){
            throw new EntityNotFoundException("Client not found!");
        }

        client.get().setName(dto.name());
        client.get().setCpf(dto.cpf());
        client.get().setPhoneNumber(dto.phoneNumber());
        client.get().setBirthDate(dto.birthDate());
        client.get().setPreferences(processCategories(dto.preferences()));
        client.get().setSocialName(dto.socialName());
        client.get().setPhoto(dto.photo());

        client.get().getAddress().addFromViaCepRequest(viaCepService.requestToViaCep(dto.cep()));
        client.get().getAddress().setAddressNumber(dto.addressNumber());
        client.get().getAddress().setComplement(dto.complement());
        client.get().getAddress().setContactPhone(dto.phoneNumber());

        clientRepository.save(client.get());

        return new ClientResponseDto(client.get());
    }

    @Transactional
    public void delete(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if(client.isEmpty()){
            throw new EntityNotFoundException("Client not found!");
        }
        clientRepository.deleteById(client.get().getId());
    }

    private Set<Category> processCategories(Set<String> preferences) {
        if (preferences == null || preferences.isEmpty()) {
            return new HashSet<>(categoryRepository.findAll());
        }

        Set<CategoryType> categoryTypes = preferences.stream()
                .map(displayName -> CategoryType.fromDisplayName(displayName.trim()))
                .collect(Collectors.toSet());

        return categoryRepository.findByNameIn(categoryTypes);
    }
}
