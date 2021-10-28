package cz.cvut.fit.tjv.hlavaj39.semestral.server.domain;

import java.util.Objects;
import java.util.Set;

public class CTrip {
    private int m_Id;
    private String m_Desination;
    private Set<CScout> m_Participants;

    public CTrip(int m_Id, String m_Desination, Set<CScout> m_Participants) {
        this.m_Id = m_Id;
        this.m_Desination = m_Desination;
        this.m_Participants = m_Participants;
    }

    public CTrip(int m_Id) {
        this.m_Id = m_Id;
        this.m_Desination = null;
        this.m_Participants = null;
    }

    public int getM_Id() {
        return m_Id;
    }

    public void setM_Id(int m_Id) {
        this.m_Id = m_Id;
    }

    public String getM_Desination() {
        return m_Desination;
    }

    public void setM_Desination(String m_Desination) {
        this.m_Desination = m_Desination;
    }

    public Set<CScout> getM_Participants() {
        return m_Participants;
    }

    public void setM_Participants(Set<CScout> m_Participants) {
        this.m_Participants = m_Participants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CTrip cTrip = (CTrip) o;
        return m_Id == cTrip.m_Id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_Id);
    }
}
