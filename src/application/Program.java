package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

import entities.Sale;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner scanner = new Scanner(System.in);
		System.out.print("Entre o caminho do arquivo: ");
		String path = scanner.nextLine();

		List<Sale> sales = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line = br.readLine();
			while (line != null) {
				String[] fields = line.split(",");
				Integer month = Integer.parseInt(fields[0]);
				Integer year = Integer.parseInt(fields[1]);
				String seller = fields[2];
				Integer items = Integer.parseInt(fields[3]);
				Double total = Double.parseDouble(fields[4]);

				Sale sale = new Sale(month, year, seller, items, total);
				sales.add(sale);

				line = br.readLine();
			}
			Set<String> uniqueSellers = new HashSet<>();
			for (Sale sale : sales) {
				uniqueSellers.add(sale.getSeller());
			}
			System.out.println("\nTotal de vendas por vendedor:");
			for (String seller : uniqueSellers) {
				Double totalSold = sales.stream().filter(s -> s.getSeller().equals(seller)).mapToDouble(Sale::getTotal)
						.sum();
				System.out.printf("%s - R$ %.2f%n", seller, totalSold);
			}
		} catch (IOException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		
		scanner.close();
	}
}
