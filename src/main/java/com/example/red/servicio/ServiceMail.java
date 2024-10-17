package com.example.red.servicio;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.example.red.gui.model.ModelMessage;

public class ServiceMail {
    public ModelMessage sendMain(String toEmail, String code) {
        ModelMessage ms = new ModelMessage(false, "");
        String from = "reduniversidadpsjb@gmail.com"; // Asegúrate de usar un correo válido
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        // Credenciales del correo

        String username = "reduniversidadpsjb@gmail.com"; // Cambia esto por tu correo
        String password = "igqzqukgxerlgjgx"; // Cambia esto por tu contraseña

        // Crear la sesión de correo
        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Crear el mensaje
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("Codigo de Verificacion");
            message.setText("Hola!, Tu codigo de verificacion es: " + code); // Texto del correo
            Transport.send(message);
            ms.setSuccess(true); // Si se envía correctamente
        } catch (MessagingException e) {
            if (e.getMessage().equals("Direccion no valida")) {
                ms.setMessage("Email no valido"); // Mensaje si el email es inválido
            } else {
                ms.setMessage("Error"); // Mensaje de error general
            }
        }
        return ms; // Retornar el estado del envío
    }
}
