package com.example.user;

import models.Post;
import models.Voiture;

public interface MyListener {
    public void onClickListener(Voiture voiture);
    public void onClickListener(Post post) ;


}
