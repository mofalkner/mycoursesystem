package dataaccess;

import domain.Student;

import java.util.List;
import java.util.Optional;

public interface MyStudentRepository extends BaseRepository<Student, Long> {
    List<Student> findAllStudentsbyVN(String name);

    List<Student> findAllStudentsbyBirthyear(String birthyear);

    List<Student> findAllStudentsbyId(Long id);
}