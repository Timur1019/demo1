package com.example.demo.controller;

import com.example.demo.dto.ClientForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/client")
public class ClientFormController {

    @Operation(summary = "Submit client form", description = "Submits the client form and sends the details to Telegram.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Form successfully submitted", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    @PostMapping("/submit")
    public ResponseEntity<String> submitClientForm(
            @RequestBody @Schema(description = "Client form details", required = true) ClientForm clientForm) {
        sendToTelegram(clientForm);
        return ResponseEntity.ok("Форма успешно отправлена");
    }

    private void sendToTelegram(ClientForm clientForm) {
        String botToken = "7219282300:AAGgptkOSx6J4dpNGe6TgQPlNiY_2sLs6jc";
        String chatId = "-1002442792451";

        String message = String.format(
                "Новая заявка от клиента:\n" +
                        "Имя: %s %s\n" +
                        "Email: %s\n" +
                        "Телефон: %s\n" +
                        "Описание: %s",
                clientForm.getFirstName(),
                clientForm.getLastName(),
                clientForm.getEmail(),
                clientForm.getPhone(),
                clientForm.getDescription()
        );

        String url = String.format(
                "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s",
                botToken, chatId, message
        );

        RestTemplate restTemplate = new RestTemplate();
        try {
            String response = restTemplate.getForObject(url, String.class);
            System.out.println("Response from Telegram API: " + response);
        } catch (Exception e) {
            System.err.println("Failed to send message to Telegram: " + e.getMessage());
            throw new RuntimeException("Ошибка при отправке сообщения в Telegram", e);
        }
    }
}