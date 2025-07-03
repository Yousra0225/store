package com.yousra.store;

import com.yousra.store.service.OrderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class StoreApplication {

	public static void main(String[] args) {
		ApplicationContext  context= SpringApplication.run(StoreApplication.class, args);
		var orderService = context.getBean(OrderService.class); // Création de l'objet + injection de dépendances automatiquement
		// var orderService = new OrderService(new StripePaymentService()); création de l'objet manuellement + injection de dépendances
		orderService.placeOrder();
		//System.out.println("_____________________");
		//var orderService2 = new OrderService(new PaypalPaymentService());
		//orderService2.placeOrder();



	}

}
