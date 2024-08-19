package mk.ukim.finki.AdvancedTasks2;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Comment {
    private final String username;
    private final String commentId;
    private final String content;
    private int likes;
    private List<Comment> comments;

    public Comment(String username, String commentId, String content) {
        this.username = username;
        this.commentId = commentId;
        this.content = content;
        this.likes = 0;
        this.comments = new ArrayList<>();
    }

    public void addComment(String username, String commentId, String content, String replyToId) {

        if (this.commentId.equals(replyToId)) {
            comments.add(new Comment(username,commentId,content));
        }
        comments.forEach(c->c.addComment(username,commentId,content,replyToId));
    }


    public void likeComment(String commentId) {
        if (this.commentId.equals(commentId)) {
            likes++;
            return;
        }
        comments.forEach(c -> c.likeComment(commentId));
    }

    public int likesNum(){
        return likes+comments.stream().mapToInt(Comment::likesNum).sum();
    }

    public String print(String indent) {
        comments=comments.stream().sorted(Comparator.comparing(Comment::likesNum).reversed()).collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%sComment: %s\n%sWritten by: %s\n%sLikes: %d%n", indent, content, indent, username, indent, likes));
        comments.forEach(c -> sb.append(c.print(indent + "    ")));
        return sb.toString();
    }
}

class Post {

    private final String username;
    private final String postContent;

    private List<Comment> comments;

    public Post(String username, String postContent) {
        this.username = username;
        this.postContent = postContent;
        this.comments = new ArrayList<>();
    }

    public void addComment(String username, String commentId, String content, String replyToId) {
        if (replyToId == null) {
            comments.add(new Comment(username, commentId, content));
        }

        comments.forEach(c -> c.addComment(username, commentId, content, replyToId));
    }

    public void likeComment(String commentId) {
        comments.forEach(c -> c.likeComment(commentId));
    }

    @Override
    public String toString() {
        comments=comments.stream().sorted(Comparator.comparing(Comment::likesNum).reversed()).collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Post: %s%n", postContent));
        sb.append(String.format("Written by: %s%n", username));
        sb.append("Comments:\n");
        comments.forEach(c -> sb.append(c.print("        ")));
        return sb.toString();
    }
}

public class PostTester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String postAuthor = sc.nextLine();
        String postContent = sc.nextLine();

        Post p = new Post(postAuthor, postContent);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split(";");
            String testCase = parts[0];

            if (testCase.equals("addComment")) {
                String author = parts[1];
                String id = parts[2];
                String content = parts[3];
                String replyToId = null;
                if (parts.length == 5) {
                    replyToId = parts[4];
                }
                p.addComment(author, id, content, replyToId);
            } else if (testCase.equals("likes")) { //likes;1;2;3;4;1;1;1;1;1 example
                for (int i = 1; i < parts.length; i++) {
                    p.likeComment(parts[i]);
                }
            } else {
                System.out.println(p);
            }

        }
    }
}
