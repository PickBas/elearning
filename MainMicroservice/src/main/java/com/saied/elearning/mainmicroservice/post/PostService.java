package com.saied.elearning.mainmicroservice.post;

import com.saied.elearning.acmq.MessageSenderService;
import com.saied.elearning.mainmicroservice.report_microservice.ReportDto;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final RestTemplate restTemplate;
    private final MessageSenderService messageSenderService;

    public Post createPost(String title, String content) {
        log.info("Creating post with title: {} and content: {}", title, content);
        Post post = Post.builder()
            .title(title)
            .content(content)
            .build();
        sendActionToReport("Created Post");
        return postRepository.save(post);
    }

    public Post getPost(Long id) {
        log.info("Getting post with id: {}", id);
        sendActionToReport("Got Post");
        return postRepository
            .findById(id)
            .orElseThrow(
                () -> new IllegalArgumentException("Could not find post with id: " + id)
            );
    }

    private void sendActionToReport(String action) {
        String timestamp = LocalDateTime.now().toString();
        ReportDto report = new ReportDto(action, timestamp);
        log.info("Sending action: {}; timestamp: {} to report microservice", action, timestamp);
        messageSenderService.sendMessage("report-queue", report);
    }
}
