package com.example.conference.dao;

import com.example.conference.model.Conference;
import com.example.conference.model.Section;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConferenceDao extends AbstractDao {
    private static final Logger logger = LoggerFactory.getLogger(ConferenceDao.class);

    private static final String FIND_ALL_CONFERENCES = "SELECT id, name, description, date, location FROM conferences ORDER BY date DESC";

    public List<Conference> findAllConferences() {
        List<Conference> conferences = new ArrayList<>();
        Connection conn = null;
        try {
            conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(FIND_ALL_CONFERENCES);

            while (rs.next()) {
                conferences.add(mapConference(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding all conferences", e);
        } finally {
            close(conn);
        }
        return conferences;
    }

    private Conference mapConference(ResultSet rs) throws SQLException {
        return Conference.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .date(rs.getTimestamp("date").toLocalDateTime())
                .location(rs.getString("location"))
                .build();
    }

    public void saveConference(Conference conference) throws SQLException {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public List<Section> findSectionsByConferenceId(long conferenceId) {
        return List.of();
    }

    public void saveSection(Section section) throws SQLException {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}