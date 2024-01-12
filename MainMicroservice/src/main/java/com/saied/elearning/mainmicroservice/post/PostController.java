package com.saied.elearning.mainmicroservice.post;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{id}")
    public Post getPost(@PathVariable("id") Long id) {
        return postService.getPost(id);
    }

    @PostMapping("/create")
    public Post createPost(@RequestBody PostDto postDto) {
        return postService.createPost(postDto.title(), postDto.content());
    }
}
