/**
 * 
 */
package um.haberes.rest.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

/**
 * @author daniel
 *
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Auditable {

	@CreatedDate
	@Column(name = "created", nullable = false, updatable = false)
	private LocalDateTime created;

	@LastModifiedDate
	@Column(name = "updated")
	private LocalDateTime updated;

}
