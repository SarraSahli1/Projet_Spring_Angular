package com.example.springbootesprit.config;

import com.example.springbootesprit.entities.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

@Getter
    @Setter
    @Entity
    @NoArgsConstructor
    public class PasswordResetToken {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long token_id;
        private String token;
        private Date expirationTime;
        private static final int EXPIRATION_TIME = 5;

        @OneToOne
        @JoinColumn(name = "user_id")
        private User user;

        public PasswordResetToken(String token, User user) {
            super();
            this.token = token;
            this.user = user;
            this.expirationTime = this.getTokenExpirationTime();
        }

        public PasswordResetToken(String token) {
            super();
            this.token = token;
            this.expirationTime = this.getTokenExpirationTime();
        }

        public Date getTokenExpirationTime() {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(new Date().getTime());
            calendar.add(Calendar.MINUTE, EXPIRATION_TIME);
            return new Date(calendar.getTime().getTime());
        }

}
