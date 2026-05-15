package org.serratec.concessionaria.model;

import java.time.LocalDateTime;

public record ErrorMessage(String message, LocalDateTime data) {
}
