package ra.jpa.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ra.jpa.entity.Person;

import java.util.List;

@Repository // ko can thiết v đã kế thừa JPARepository
public interface PersonRepository extends JpaRepository<Person,Integer> {

    // interface gốc : Repository
    // Repository<T,ID> : ko có bất cứ cái gì : nguyên mẫu , chuẩn để xây dựng cá repository
    // interface con : CrudRepository, PagingAndSortingRepository, ListCrudRepository,ListPagingAndSortingRepository
    // interface cuôi cùng (quan trọng nhất) : JpaRepository

    // chỉ cân kề thừa inteface của repositorty th mặc định jpa repository sẽ tạo ra các phương thức cần thiết cho interface này
    // bao gồm
    // 4 phương thức c,r,u,d
    // thêm mới và cập nhật : save(T t)
    // lấy tất cả danh sách bản ghi về : findALl()
    // xóa : deleteById(E id), delete(T t)
    // tìm kiếm : findById(E id);

    // phân trang và sắp xếp

    // khai báo phương thuc theo tên
    // findBy , findAllBy, existBy, count
    List<Person> findAllByNameContaining(String name);

    // khai phương thức bth
    @Query(value = "select count(P) from Person P where P.name like concat('%',:name,'%')")  // JPQL

    long countedByName(@Param("name") String name);
}
