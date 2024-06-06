package ra.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.jpa.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
}
