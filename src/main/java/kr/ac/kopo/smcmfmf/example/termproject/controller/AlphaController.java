package kr.ac.kopo.smcmfmf.example.termproject.controller;

import kr.ac.kopo.smcmfmf.example.termproject.domain.Car;
import kr.ac.kopo.smcmfmf.example.termproject.domain.Member;
import kr.ac.kopo.smcmfmf.example.termproject.domain.Purchase;
import kr.ac.kopo.smcmfmf.example.termproject.repository.CarRepository;
import kr.ac.kopo.smcmfmf.example.termproject.repository.MemberRepository;
import kr.ac.kopo.smcmfmf.example.termproject.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AlphaController {

    @Autowired private PurchaseRepository purchaseRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private CarRepository carRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String requestMethod() { return "alpha"; }

    @GetMapping("/alpha1") public String alpha1() { return "alpha1"; }
    @GetMapping("/alpha2") public String alpha2() { return "alpha2"; }
    @GetMapping("/alpha3") public String alpha3() { return "alpha3"; }

    @GetMapping("/signup")
    public String signupForm() {
        return "signup";
    }

    @PostMapping("/join")
    public String join(Member member) {
        member.setUserPassword(passwordEncoder.encode(member.getUserPassword()));
        member.setRole("ROLE_USER");

        memberRepository.save(member);

        return "redirect:/login";
    }

    @PostMapping("/alpha4")
    public String showAlpha4(@RequestParam String car,
                             @RequestParam String color,
                             @RequestParam String why,
                             @RequestParam String date,
                             Model model) {
        model.addAttribute("car", car);
        model.addAttribute("color", color);
        model.addAttribute("why", why);
        model.addAttribute("date", date);
        return "alpha4";
    }

    @PostMapping("/order/save")
    public String saveOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam String carModel,
            @RequestParam String carColor,
            @RequestParam String usagePurpose,
            @RequestParam String deliveryDate,
            @RequestParam String consumerPhone
    ) {
        Member member = memberRepository.findByUserId(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("로그인 회원 정보 없음"));

        Car car = carRepository.findByModelAndColor(carModel, carColor)
                .orElseGet(() -> carRepository.save(new Car(null, carModel, carColor)));

        Purchase purchase = new Purchase();
        purchase.setMember(member);
        purchase.setCar(car);
        purchase.setUsagePurpose(usagePurpose);
        purchase.setDeliveryDate(deliveryDate);
        purchase.setAddress(member.getUserAddress());
        purchase.setPhone(consumerPhone);

        purchaseRepository.save(purchase);

        return "redirect:/alpha5";
    }

    @GetMapping("/alpha5") public String alpha5() { return "alpha5"; }
    @GetMapping("/login") public String login() { return "login"; }

    // ================= 관리자 모드 =================
    @GetMapping("/admin/list")
    public String adminList(Model model) {
        List<Purchase> list = purchaseRepository.findAll();
        model.addAttribute("list", list);
        return "admin";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        purchaseRepository.deleteById(id);
        return "redirect:/admin/list";
    }

    @GetMapping("/admin/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        Purchase purchase = purchaseRepository.findById(id).orElse(null);
        model.addAttribute("item", purchase);
        return "admin_update";
    }

    @PostMapping("/admin/update")
    public String updateOrder(
            @RequestParam Long id,
            @RequestParam String carModel,
            @RequestParam String carColor,
            @RequestParam String usagePurpose,
            @RequestParam String deliveryDate,
            @RequestParam String phone
    ) {
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다"));

        Car car = carRepository.findByModelAndColor(carModel, carColor)
                .orElseGet(() -> {
                    Car newCar = new Car();
                    newCar.setModel(carModel);
                    newCar.setColor(carColor);
                    return carRepository.save(newCar);
                });

        purchase.setCar(car);
        purchase.setUsagePurpose(usagePurpose);
        purchase.setDeliveryDate(deliveryDate);
        purchase.setPhone(phone);

        purchaseRepository.save(purchase);

        return "redirect:/admin/list";
    }
}