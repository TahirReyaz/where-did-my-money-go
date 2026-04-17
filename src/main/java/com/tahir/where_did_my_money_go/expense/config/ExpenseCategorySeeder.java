package com.tahir.where_did_my_money_go.expense.config;

import com.tahir.where_did_my_money_go.expense.entity.ExpenseCategory;
import com.tahir.where_did_my_money_go.expense.repository.ExpenseCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ExpenseCategorySeeder implements CommandLineRunner {

    private final ExpenseCategoryRepository repository;

    @Override
    public void run(String... args) {
        repository.findByName("others")
                .orElseGet(() -> repository.save(
                        ExpenseCategory.builder()
                                .name("others")
                                .build()));
    }
}