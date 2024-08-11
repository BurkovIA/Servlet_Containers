package org.example.repository;
import org.example.model.Post;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

// Stub
public class PostRepository {
    private List<Post> posts = new ArrayList<>();
    private long postIdCounter = 1;
    public synchronized List<Post> all() {
        return Collections.unmodifiableList(posts);
    }
    public synchronized Optional<Post> getById(long id) {
        return posts.stream()
                .filter(post -> post.getId() == id)
                .findFirst();
    }
    public synchronized Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(postIdCounter++);
            posts.add(post);
        } else {
            int index = -1;
            for (int i = 0; i < posts.size(); i++) {
                if (posts.get(i).getId() == post.getId()) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                posts.set(index, post);
            } else {posts.add(post);
            }
        }
        return post;
    }
    public synchronized void removeById(long id) {
        posts.removeIf(post -> post.getId() == id);
    }
}