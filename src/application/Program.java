package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Product;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		System.out.print("Enter full file path: ");
		String path = sc.nextLine();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			List<Product> list = new ArrayList<>();

			String line = br.readLine();
			while (line != null) {
				String[] fields = line.split(",");
				list.add(new Product(fields[0], Double.parseDouble(fields[1])));
				line = br.readLine();
			}

			double avg = list.stream()
					.map(p -> p.getPrice()) // Define cada produto p como p.getPrice(). Para cada produto p da minha list eu querer apenas o price.
					.reduce(0.0, (x,y) -> x + y) / list.size(); // Permite fazer o somatório dos preços e divide pelo tamanho da list.

			System.out.println("Average price: " + String.format("%.2f", avg));
			
			// Compara duas Strings (s1 e s2), comparando as duas convertidas em maiúsculas.
			// Como é String, automaticamente o Comparator compara pela ordem alfabética.
			Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());

			List<String> names = list.stream()
					.filter(p -> p.getPrice() < avg) // Filtra todos os produtos da list que tenham preços menores que avg. 
					.map(p -> p.getName()) // Define cada produto p como p.getName(). Para cada produto p da minha list eu vou querer apenas o name. 
					.sorted(comp.reversed()) // .sorted() ordena os elementos em ordem crescente, mas se tem o .reversed() será ordenado em decrescente.
					.collect(Collectors.toList());

			names.forEach(System.out::println);

		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		sc.close();
	}
}