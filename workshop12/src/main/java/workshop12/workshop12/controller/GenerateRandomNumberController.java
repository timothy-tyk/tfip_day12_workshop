package workshop12.workshop12.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import workshop12.workshop12.exception.RandomNumberException;
import workshop12.workshop12.models.Generate;


@Controller
@RequestMapping(path="/rand")
public class GenerateRandomNumberController {
  @GetMapping(path = "/show")
  public String showRandForm(Model model){
    Generate g = new Generate();
    model.addAttribute("generateObj", g);
    return "generate";
  }  

  @PostMapping(path = "/generate")
  // Model Attribute ties in to the Generate Object
  public String postRandNum(@ModelAttribute Generate generate, Model model){
    // Call getNumberValue to get the number input
    this.randomizeNumbers(model, generate.getNumberValue());
    return "result";
  }

  // GetMapping (string query) -> RequestParam
  // GetMapping (path variable) -> PathVariable

  public void randomizeNumbers(Model model, int numberInput){
    int maxNumberCount = 30;
    List<Integer> selectedNumbers = new ArrayList<Integer>();

    if(numberInput<1 || numberInput>maxNumberCount+1){
      throw new RandomNumberException();
    }

    while(selectedNumbers.size()<numberInput){
      Random random = new Random();
      Integer randInt = random.nextInt(maxNumberCount+1);
      if (!selectedNumbers.contains(randInt)){
        selectedNumbers.add(randInt);
      }
    }
    //Send back list of numberInput and selectedNumbers to the model
    model.addAttribute("numberInput", numberInput);
    model.addAttribute("selectedNumbers", selectedNumbers.toArray());
  }
}
