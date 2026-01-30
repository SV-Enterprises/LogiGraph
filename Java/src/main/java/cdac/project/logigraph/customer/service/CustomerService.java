package cdac.project.logigraph.customer.service;

import cdac.project.logigraph.auth.entity.User;
import cdac.project.logigraph.auth.repository.UserRepository;
import cdac.project.logigraph.customer.entity.Customer;
import cdac.project.logigraph.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    public CustomerService(
            UserRepository userRepository,
            CustomerRepository customerRepository
    ) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * Resolve CUSTOMER ID from authenticated username.
     *
     * READ-ONLY
     * - DB is source of truth
     * - No JWT business leakage
     */
    @Transactional(readOnly = true)
    public Integer getCustomerIdByUsername(String username) {

        User user =
                userRepository.findByUsername(username)
                        .orElseThrow(() ->
                                new IllegalStateException(
                                        "User not found: " + username
                                )
                        );

        Customer customer =
                customerRepository.findByUserId(user.getId())
                        .orElseThrow(() ->
                                new IllegalStateException(
                                        "Customer profile not found for userId: "
                                                + user.getId()
                                )
                        );

        return customer.getId();
    }

    /**
     * Used during registration to prevent duplicate customer profiles.
     */
    @Transactional(readOnly = true)
    public boolean customerExistsForUser(Integer userId) {
        return customerRepository.existsByUserId(userId);
    }
}
