package com.example.chanhyunguniversity.service;

import com.example.chanhyunguniversity.domain.NoticeEntity;
import com.example.chanhyunguniversity.repository.NoticeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    public void createNotice(String title, String content ,boolean fixNotice) {
        NoticeEntity noticeEntity = new NoticeEntity();
        noticeEntity.setTitle(title);
        noticeEntity.setContent(content);
        noticeEntity.setFixNotice(fixNotice);
        noticeEntity.setCreatedAt(LocalDateTime.now());

        this.noticeRepository.save(noticeEntity);
    }

    @Transactional
    public void updateNotice(Long id, String title, String content ,boolean fixNotice) {
        NoticeEntity notice = this.getNotice(id);
        notice.setTitle(title);
        notice.setContent(content);
        notice.setFixNotice(fixNotice);
        notice.setCreatedAt(LocalDateTime.now());
    }
    @Transactional
    public void deleteNotice(Long id) {
        NoticeEntity notice = this.getNotice(id);
        this.noticeRepository.delete(notice);
    }
    public List<NoticeEntity> getList() {
        List<NoticeEntity> fixedNotices = noticeRepository.findByFixNoticeOrderByCreatedAtDesc(true);
        List<NoticeEntity> normalNotices = noticeRepository.findByFixNoticeOrderByCreatedAtDesc(false);

        List<NoticeEntity> allNotices = new ArrayList<>(fixedNotices);
        allNotices.addAll(normalNotices);

        return allNotices;

    }

    public NoticeEntity getNotice(Long id) {
        return this.noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("찾을 수 없는 공지입니다. ID: " + id));
    }
}
