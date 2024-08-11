package org.example;
import org.example.model.Post;
import org.example.repository.PostRepository;

public class Main {
    public static void main(String[] args) {
        PostRepository postRepository = new PostRepository();
        synchronized (postRepository) {
            Post newPost = new Post(0, "New Post Content");
            postRepository.save(newPost);
        }
    }
}