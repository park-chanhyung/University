package com.example.chanhyunguniversity.service;

import com.example.chanhyunguniversity.domain.AskEntity;
import com.example.chanhyunguniversity.domain.UserEntity;
import com.example.chanhyunguniversity.repository.AskRepository;
import com.example.chanhyunguniversity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AskService
{
    private final AskRepository askRepository;
    private final UserRepository userRepository;


    public void createAsk(String title, String content, String phoneNumber, String username) {
        AskEntity ask = new AskEntity();
        ask.setTitle(title);
        ask.setContent(content);
        ask.setPhoneNumber(phoneNumber);
        ask.setCreatedAt(LocalDateTime.now());
        UserEntity user = userRepository.findByusername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        ask.setUser(user);
        askRepository.save(ask);
    }

    public List<AskEntity> getList() {
        return this.askRepository.findAll();

    }

    public void answerAsk(Long id, String answer) {
        AskEntity ask = askRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Ask not found"));
        ask.setAnswer(answer);
        ask.setAnsweredAt(LocalDateTime.now());
        ask.setStatus("답변완료");
        askRepository.save(ask);
    }


    public List<AskEntity> getAskByUsername(String username) {

        UserEntity user = userRepository.findByusername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return this.askRepository.findByUserOrderByCreatedAtDesc(user);
    }
    public AskEntity getAskById(Long id) {
        return askRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ask not found"));
    }
}
