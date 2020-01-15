//package by.vlad.springproject1.repository;
//
//import by.vlad.springproject1.entity.enums.Project;
//import by.vlad.springproject1.entity.enums.Task;
//import by.vlad.springproject1.entity.enums.User;
//import by.vlad.springproject1.entity.enums.nontable.ProjectTaskType;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.awt.print.Pageable;
//import java.util.List;
//
//@Repository
//public interface TaskRepository extends CrudRepository<Task, Long> {
//    List<Task> findAllByProject(Project project, Pageable pageable);
//    @Query(value = "select t.type, count(*)\n" +
//            "from project p\n" +
//            " join task t on p.id = t.project_id\n" +
//            "where p.id = :id\n" +
//            "group by t.type\n" +
//            "order by t.type", nativeQuery = true)
//    List<ProjectTaskType> findTaskTypesByProject(@Param("id") Long projectId);
//    List<Task> findAllByCreator(User user, Pageable pageable);
//}