package com.clubeevantagens.authmicroservice.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Embeddable
public class ResetPasswordToken {
    @Setter
    @Column(name = "reset_password_token")
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reset_password_expiry_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private LocalDateTime expiryDate;

    // VERIFICA DATA DE "resetPasswordExpiryDate"
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiryDate);
    }

    public void setExpiryDate(long expiryTimeInMinutes) {
        LocalDateTime now = LocalDateTime.now();
        this.expiryDate = now.plusMinutes(expiryTimeInMinutes);
    }
}
