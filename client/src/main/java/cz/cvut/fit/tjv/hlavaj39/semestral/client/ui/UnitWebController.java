package cz.cvut.fit.tjv.hlavaj39.semestral.client.ui;

import cz.cvut.fit.tjv.hlavaj39.semestral.client.data.UnitClient;
import cz.cvut.fit.tjv.hlavaj39.semestral.client.model.UnitDto;
import cz.cvut.fit.tjv.hlavaj39.semestral.client.model.UnitWebModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/units")
public class UnitWebController {
    private final UnitClient unitClient;

    public UnitWebController(UnitClient unitClient) {
        this.unitClient = unitClient;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("units", unitClient.readAll());
        return "units";
    }

    @GetMapping("/location")
    public String byLocation(@RequestParam(value = "location") String location, Model model) {
        if(location.isEmpty())
            return list(model);
        model.addAttribute("units", unitClient.readByLocation(location));
        return "units";
    }

    @GetMapping("/edit")
    public String editUnit(@RequestParam Integer number, Model model) {
        model.addAttribute("unitDto", unitClient.readById(number));
        return "unitEdit";
    }

    @PostMapping("/edit")
    public String editUnitSubmit(@ModelAttribute UnitDto unitDto, Model model) {
        model.addAttribute("unitDto", unitClient.update(unitDto));
        return "unitEdit";
    }

    @GetMapping("/add")
    public String addUnit(Model model) {
        UnitWebModel mod = new UnitWebModel();
        mod.setErrorState(UnitWebModel.STATE.undefined);
        model.addAttribute("unitWebModel", mod);
        return "unitAdd";
    }

    @PostMapping("/add")
    public String addUnitSubmit(@ModelAttribute UnitWebModel unitWebModel, Model model) {
        model.addAttribute("unitWebModel",
                unitClient.create(unitWebModel)
                        .onErrorResume(WebClientResponseException.Conflict.class, e -> Mono.just(new UnitWebModel(UnitWebModel.STATE.numberCollision, unitWebModel)))
                        .onErrorResume(WebClientResponseException.BadRequest.class, e -> Mono.just(new UnitWebModel(UnitWebModel.STATE.noNumber, unitWebModel))));
        return "unitAdd";
    }

    @GetMapping("/delete")
    public String addUnit(@RequestParam Integer number, Model model) {
        model.addAttribute("", unitClient.delete(number));
        return "redirect:/units";
    }


}
