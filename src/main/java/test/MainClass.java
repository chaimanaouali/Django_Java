package test;

import models.Post;
import service.ServicePost;
import utils.DBConnection;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class MainClass {

    public static void main(String[] args) {
        DBConnection cn1 = DBConnection.getInstance();

        // Get the current date and time as a Timestamp
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        // Instantiate a new Post object with the current Timestamp
        Post p = new Post("hi", "very nice", "image", "categorie", currentTimestamp );
        Post updatedPost = new Post(64, "New Title", "Updated description", "Updated image", "Updated category", currentTimestamp);

        ServicePost sp = new ServicePost();

        try {
            sp.insertOne(p);
            // sp.updateOne(updatedPost);
            sp.deleteOne(updatedPost);
            List<Post> allPosts = sp.selectAll();
            for (Post post : allPosts) {
                System.out.println(post);
            }
            // System.out.println(sp.selectAll());
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }
}
