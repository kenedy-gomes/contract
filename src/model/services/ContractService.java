package model.services;

import java.time.LocalDate;

import model.entities.Contract;
import model.entities.Installment;

public class ContractService {

	private OnlinePaymentService onlinepaymentservice;
	public ContractService(OnlinePaymentService onlinepaymentservice) {
		this.onlinepaymentservice = onlinepaymentservice;
	}
	
	public void processContract(Contract contract, int months) {
		
		double basicquota = contract.getTotalValue() / months;	
		for(int i = 1; i <= months; i++) {
			LocalDate dueDate = contract.getDate().plusMonths(i);
			double interest = onlinepaymentservice.interest(basicquota, i);
			double fee = onlinepaymentservice.paymentFee(basicquota + interest);
			double quota = basicquota + interest + fee;
			
			contract.getInstallment().add(new Installment(dueDate, quota));
		}	
		
	}
	
}
