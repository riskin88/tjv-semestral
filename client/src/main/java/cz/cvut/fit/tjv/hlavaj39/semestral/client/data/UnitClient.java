package cz.cvut.fit.tjv.hlavaj39.semestral.client.data;

import cz.cvut.fit.tjv.hlavaj39.semestral.client.model.UnitDto;
import cz.cvut.fit.tjv.hlavaj39.semestral.client.model.UnitWebModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UnitClient {
    private static final String ONE_URI = "/{id}";
    private final WebClient unitWebClient;

    public UnitClient(@Value("${backend_url}") String backendUrl) {
        unitWebClient = WebClient.create(backendUrl + "/units");
    }

    public Mono<UnitWebModel> create(UnitDto unit) {
        return unitWebClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(unit)
                .retrieve()
                .bodyToMono(UnitWebModel.class);
    }

    public Flux<UnitWebModel> readAll() {
        return unitWebClient.get()
                .retrieve()
                .bodyToFlux(UnitWebModel.class);
    }

    public Flux<UnitWebModel> readByLocation(String location) {
        return unitWebClient.get()
                .uri(uriBuilder -> uriBuilder
                .path("/by")
                .queryParam("location", location)
                .build())
                .retrieve()
                .bodyToFlux(UnitWebModel.class);
    }

    public Mono<Void> delete(Integer number) {
        return unitWebClient.delete()
                .uri(ONE_URI, number)
                .retrieve()
                .bodyToMono(Void.TYPE);
    }

    public Mono<UnitWebModel> readById(Integer number) {
        return unitWebClient.get()
                .uri(ONE_URI, number).retrieve()
                .bodyToMono(UnitWebModel.class);
    }

    public Mono<UnitWebModel> update(UnitDto unit) {
        return unitWebClient.put() // HTTP PUT
                .uri(ONE_URI, unit.getNumber()) // URI /{id}
                .contentType(MediaType.APPLICATION_JSON) // HTTP header
                .bodyValue(unit) // HTTP body
                .retrieve()
                .bodyToMono(UnitWebModel.class);
    }

}
