package service;

import com.example.django2.data;
import models.Post;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicePost  implements CRUD<Post> {

    private Connection cnx ;
    public ServicePost() {
        cnx = DBConnection.getInstance().getCnx();
    }


    public void insertOne(Post post) throws SQLException {
        String req = "INSERT INTO `post`(`titre`, `description`, `image_name`, `categorie`, `updated_at`) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setString(1, post.getTitre());
            preparedStatement.setString(2, post.getDescription());
            String path = data.path;
            path = path.replace("\\", "\\\\");
            preparedStatement.setString(3, path);            preparedStatement.setString(4, post.getCategorie());
            // Set the updated_at attribute to the current timestamp
            preparedStatement.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis()));

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Post Added !");
            } else {
                System.out.println("No post added.");
            }
        } catch (SQLException e) {
            System.err.println("Error inserting post: " + e.getMessage());
        }
    }


    public void updateOne(Post post) throws SQLException {
        if (post.getId() == 0) {
            System.err.println("Error updating post: Invalid post ID.");
            return;
        }

        String req = "UPDATE `post` SET `titre`=?, `description`=?, `image_name`=?, `categorie`=?, `updated_at`=? WHERE `id`=?";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setString(1, post.getTitre());
            preparedStatement.setString(2, post.getDescription());
            preparedStatement.setString(3, post.getImage_name());
            preparedStatement.setString(4, post.getCategorie());

            // Check if updated_at is not null before invoking getTime()
            Timestamp timestamp = (Timestamp) post.getUpdated_at();
            if (timestamp != null) {
                preparedStatement.setTimestamp(5, new java.sql.Timestamp(timestamp.getTime()));
            } else {
                preparedStatement.setNull(5, Types.TIMESTAMP);
            }

            preparedStatement.setInt(6, post.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Post updated successfully!");
            } else {
                System.out.println("No post updated.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error updating post: " + e.getMessage());
        }
    }



    public void deleteOne(Post post) throws SQLException {
        String req = "DELETE FROM `post` WHERE `id`=?";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setInt(1, post.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Post deleted successfully!");
            } else {
                System.out.println("No post deleted.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting post: " + e.getMessage());
        }
    }

    @Override
    public List<Post> selectAll() throws SQLException {
        List<Post> posts = new ArrayList<>();
        String req = "SELECT * FROM post";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req);
             ResultSet resultSet = preparedStatement.executeQuery()) {


            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String titre = resultSet.getString("titre");
                String description = resultSet.getString("description");
                String image_name = resultSet.getString("image_name");
                String categorie = resultSet.getString("categorie");
                Timestamp updated_at = resultSet.getTimestamp("updated_at");

                Post post = new Post(id, titre, description, image_name, categorie, updated_at);
                posts.add(post);
            }
        } catch (SQLException e) {
            System.err.println("Error selecting posts: " + e.getMessage());
            throw e;
        }

        return posts;
    }
}
