package io.github.jhipster.demo.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.demo.domain.Lie;

import io.github.jhipster.demo.repository.LieRepository;
import io.github.jhipster.demo.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Lie.
 */
@RestController
@RequestMapping("/api")
public class LieResource {

    private final Logger log = LoggerFactory.getLogger(LieResource.class);

    private static final String ENTITY_NAME = "lie";

    private final LieRepository lieRepository;

    public LieResource(LieRepository lieRepository) {
        this.lieRepository = lieRepository;
    }



    /**
     * POST  /lies : Create a new lie.
     *
     * @param lie the lie to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lie, or with status 400 (Bad Request) if the lie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lies")
    @Timed
    public ResponseEntity<Lie> createLie(@RequestBody Lie lie) throws URISyntaxException {
        log.debug("REST request to save Lie : {}", lie);
        if (lie.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new lie cannot already have an ID")).body(null);
        }
        Lie result = lieRepository.save(lie);
        return ResponseEntity.created(new URI("/api/lies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lies : Updates an existing lie.
     *
     * @param lie the lie to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lie,
     * or with status 400 (Bad Request) if the lie is not valid,
     * or with status 500 (Internal Server Error) if the lie couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lies")
    @Timed
    public ResponseEntity<Lie> updateLie(@RequestBody Lie lie) throws URISyntaxException {
        log.debug("REST request to update Lie : {}", lie);
        if (lie.getId() == null) {
            return createLie(lie);
        }
        Lie result = lieRepository.save(lie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lie.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lies : get all the lies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lies in body
     */
    @GetMapping("/lies")
    @Timed
    public List<Lie> getAllLies() {
        log.debug("REST request to get all Lies");
        List<Lie> lies = lieRepository.findAll();
        return lies;
    }

    /**
     * GET  /lies/:id : get the "id" lie.
     *
     * @param id the id of the lie to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lie, or with status 404 (Not Found)
     */
    @GetMapping("/lies/{id}")
    @Timed
    public ResponseEntity<Lie> getLie(@PathVariable String id) {
        log.debug("REST request to get Lie : {}", id);
        Lie lie = lieRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(lie));
    }

    /**
     * GET
     *
     * @return the ResponseEntity with status 200 (OK) and with body the lie, or with status 404 (Not Found)
     */
    @GetMapping("/lies/request")
    @Timed
    public String getLieRequest() throws UnknownHostException, InterruptedException {
        log.debug("REST request to get Lie request");
        this.doLie();
        return "Hello I'm " + InetAddress.getLocalHost().getHostName();
    }


    /**
     * DELETE  /lies/:id : delete the "id" lie.
     *
     * @param id the id of the lie to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lies/{id}")
    @Timed
    public ResponseEntity<Void> deleteLie(@PathVariable String id) {
        log.debug("REST request to delete Lie : {}", id);
        lieRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    private synchronized void doLie() throws InterruptedException {
        Thread.sleep(100);
    }

}
