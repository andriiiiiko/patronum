package ua.patronum.quicklink.mvc.service;

import ua.patronum.quicklink.data.entity.Url;
import ua.patronum.quicklink.data.repository.UrlRepository;

public class UrlService {

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public void add(Url url) {
        urlRepository.save(url);
    }



}

/*
методы для урла

create

view all

 */
