package com.tahir.where_did_my_money_go.expense.config;

import com.tahir.where_did_my_money_go.expense.entity.ExpenseCategory;
import com.tahir.where_did_my_money_go.expense.repository.ExpenseCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExpenseCategorySeeder implements CommandLineRunner {

    private final ExpenseCategoryRepository expenseCategoryRepository;

    private static final List<String> DEFAULT_CATEGORIES = List.of(
            "Food & Dining",
            "Groceries",
            "Transportation",
            "Fuel",
            "Utilities",
            "Rent",
            "Healthcare",
            "Shopping",
            "Entertainment",
            "Education",
            "Travel",
            "Personal Care",
            "Insurance",
            "Subscription",
            "SIP Investment",
            "Investment",
            "Gifts & Donations",
            "Loan Payment",
            "Group Expense",
            "Others");

    @Override
    @Transactional
    public void run(String... args) {

        for (String categoryName : DEFAULT_CATEGORIES) {

            boolean exists = expenseCategoryRepository.existsByNameIgnoreCase(categoryName);

            if (exists) {
                continue;
            }

            ExpenseCategory category = ExpenseCategory.builder()
                    .name(categoryName)
                    .build();

            expenseCategoryRepository.save(category);

            log.info("Created default expense category: {}", categoryName);
        }

        log.info("Expense category seed completed");
    }
}