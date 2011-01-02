package org.restlet.example.book.restlet.ch08.sec5.server.website;

import org.restlet.Restlet;
import org.restlet.example.book.restlet.ch07.sec2.server.AccountServerResource;
import org.restlet.example.book.restlet.ch07.sec2.server.AccountsServerResource;
import org.restlet.example.book.restlet.ch08.sec1.sub1.MailServerResource;
import org.restlet.example.book.restlet.ch08.sec1.sub2.CookieAuthenticator;
import org.restlet.example.book.restlet.ch08.sec1.sub4.MailStatusService;
import org.restlet.example.book.restlet.ch08.sec2.sub1.FeedServerResource;
import org.restlet.ext.wadl.WadlApplication;
import org.restlet.resource.Directory;
import org.restlet.routing.Extractor;
import org.restlet.routing.Redirector;
import org.restlet.routing.Router;
import org.restlet.security.MapVerifier;

/**
 * The reusable mail server application.
 */
public class MailSiteApplication extends WadlApplication {

    /**
     * Constructor.
     */
    public MailSiteApplication() {
        setName("RESTful Mail API application");
        setDescription("Example API for 'Restlet in Action' book");
        setOwner("Noelios Technologies");
        setAuthor("The Restlet Team");

        // Configure the status service
        setStatusService(new MailStatusService());
    }

    /**
     * Creates a root Router to dispatch call to server resources.
     */
    @Override
    public Restlet createInboundRoot() {
        Router router = new Router(getContext());
        router.attach("/", RootServerResource.class);
        router.attach("/accounts/", AccountsServerResource.class);
        router.attach("/accounts/{accountId}", AccountServerResource.class);
        router.attach("/accounts/{accountId}/feeds/{feedId}",
                FeedServerResource.class);
        router.attach("/accounts/{accountId}/mails/{mailId}",
                MailServerResource.class);
        router.attach("/accounts/{accountId}/contacts/{contactId}",
                ContactServerResource.class);

        // Serve static files (images, etc.)
        String rootUri = "file:///" + System.getProperty("java.io.tmpdir");
        Directory directory = new Directory(getContext(), rootUri);
        directory.setListingAllowed(true);
        router.attach("/static", directory);

        // Create a Redirector to Google search service
        String target = "http://www.google.com/search?q=site:mysite.org+{keywords}";
        Redirector redirector = new Redirector(getContext(), target,
                Redirector.MODE_CLIENT_TEMPORARY);

        // While routing requests to the redirector, extract the "kwd" query
        // parameter. For instance :
        // http://localhost:8111/search?kwd=myKeyword1+myKeyword2
        // will be routed to
        // http://www.google.com/search?q=site:mysite.org+myKeyword1%20myKeyword2
        Extractor extractor = new Extractor(getContext(), redirector);
        extractor.extractFromQuery("keywords", "kwd", true);

        // Attach the extractor to the router
        router.attach("/search", extractor);

        MapVerifier verifier = new MapVerifier();
        verifier.getLocalSecrets().put("scott", "tiger".toCharArray());

        CookieAuthenticator authenticator = new CookieAuthenticator(
                getContext(), "Cookie Test");
        authenticator.setVerifier(verifier);
        authenticator.setNext(router);
        return authenticator;

    }

}