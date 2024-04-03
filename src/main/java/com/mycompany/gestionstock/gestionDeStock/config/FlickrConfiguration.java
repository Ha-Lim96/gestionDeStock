package com.mycompany.gestionstock.gestionDeStock.config;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.Permission;
import com.github.scribejava.apis.FlickrApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.mycompany.gestionstock.gestionDeStock.service.FlickrService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

@Configuration
public class FlickrConfiguration {

    @Value("${flickr.apiKey}")
    private String apiKey;

    @Value("${flickr.apiSecret}")
    private String apiSecret;

    @Value("${flickr.appKey}")
    private String appKey;

    @Value("${flickr.appSecret}")
    private String appSecret;

/*
    //l'annotation bean pour que cette méthode soit éxécutée au démarage de l'appli

   // @Bean
    public Flickr getFlickr() throws IOException, ExecutionException, InterruptedException, FlickrException {
        Flickr flickr = new Flickr(apiKey, apiSecret, new REST());  //création d'une instance de type flickr utilisant un objet de type REST

        // création d'un objet de type auth10service avec la permission pour l'application
        OAuth10aService service = new ServiceBuilder(apiKey)
                .apiSecret(apiSecret)
                .build(FlickrApi.instance(FlickrApi.FlickrPerm.DELETE));

        final Scanner scanner = new Scanner(System.in);

        // get le requst token
        final OAuth1RequestToken request = service.getRequestToken();

        // get l'autoriationUrl (URL autorise l'appli à utiliser l'API)
        final String AuthUrl = service.getAuthorizationUrl(request);

        // Affichage de l'URL
        System.out.println(AuthUrl);
        System.out.println("Past it here >> ");

        // lecture du code d'autorisation (à récupéré à partir du site de l'API après avoir collé l'url sur le navigateur)
        final String authVerifier = scanner.nextLine();

        // Génération de l'Accesstoken pour l'application
        OAuth1AccessToken accessToken = service.getAccessToken(request, authVerifier);

        // Affichage des tokens générés
        System.out.println(accessToken.getToken());
        System.out.println(accessToken.getTokenSecret());

        // check de la validation du token généré
        Auth auth = flickr.getAuthInterface().checkToken(accessToken);

        System.out.println("--------------------------------------------------");

        //  Ré-affichage des tokens générés
        System.out.println(auth.getToken());
        System.out.println(auth.getTokenSecret());

        return flickr;
    }
 */

    @Bean
    public Flickr getFlickr() {
        Flickr flickr = new Flickr(apiKey, apiSecret, new REST());
        Auth auth = new Auth();

        auth.setPermission(Permission.DELETE);
        auth.setToken(appKey);
        auth.setTokenSecret(appSecret);

        RequestContext requestContext = RequestContext.getRequestContext();
        requestContext.setAuth(auth);

        flickr.setAuth(auth);
        return flickr;
    }

}
