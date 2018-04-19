package com.netcracker.travelplanner.models.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "integration_errors")
public class IntegrationError {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "integration_errors_seq")
    @SequenceGenerator(name = "integration_errors_seq", sequenceName = "integration_error_id_seq", allocationSize = 2)
    private int id;

    @Column(name = "module_title", nullable = false)
    private String moduleTitle;/*часть программы с ошибкой*/

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    @Column(length = 500)
    private String description;

    public IntegrationError() {
    }

    public IntegrationError(String moduleTitle, Date time, String description) {
        this.moduleTitle = moduleTitle;
        this.time = time;
        this.description = description;
    }

    public String getModuleTitle() {
        return moduleTitle;
    }

    public void setModuleTitle(String moduleTitle) {
        this.moduleTitle = moduleTitle;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        IntegrationError that = (IntegrationError) object;

        if (id != that.id) return false;
        if (moduleTitle != null ? !moduleTitle.equals(that.moduleTitle) : that.moduleTitle != null)
            return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (moduleTitle != null ? moduleTitle.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IntegrationError{" +
                "id=" + id +
                ", moduleTitle='" + moduleTitle + '\'' +
                ", time=" + time +
                ", description='" + description + '\'' +
                '}';
    }
}
