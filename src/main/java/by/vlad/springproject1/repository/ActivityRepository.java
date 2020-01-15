package by.vlad.springproject1.repository;

import by.vlad.springproject1.entity.enums.Activity;
import by.vlad.springproject1.entity.enums.Project;
import by.vlad.springproject1.entity.enums.Task;
import by.vlad.springproject1.entity.enums.User;
import by.vlad.springproject1.entity.enums.nontable.ProjectActivity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface ActivityRepository extends CrudRepository<Activity, Long> {
    List<Activity> findAllByTask(Task task, Pageable pageable);
    @Query("select a from Activity a join a.task t join t.project p where p = :project")
            List<Activity> findAllByProject(@Param("project") Project project, Pageable pageable);
    @Query("select a from Activity a join a.task t join t.project p join p.user u where u = :user")
            List<Activity> findAllByUser(@Param("user") User user, Pageable pageable);
    @Query(value = "select date(a.timestamp) as day, count(*) as activityAmount\n" +
            "from project p\n" +
            " join task t on p.id = t.project_id\n" +
            " join activity a on t.id = a.task_id\n" +
            "where p.id = :id\n" +
            "group by day\n" +
            "order by day", nativeQuery = true)
    List<ProjectActivity> findActivitiesByProject(@Param("id") Long
                                                          projectId);
    @Query(value = "select *\n" +
            "from task t\n" +
            " join (select *\n" +
            " from activity\n" +
            " where assignee_id = :id\n" +
            " order by timestamp desc\n" +
            " limit 1) as aa\n" +
            " on t.id = aa.task_id", nativeQuery = true)
    List<Activity> findAllAssigneeActivities(@Param("id") Long assigneeId, Pageable pageable);
}
