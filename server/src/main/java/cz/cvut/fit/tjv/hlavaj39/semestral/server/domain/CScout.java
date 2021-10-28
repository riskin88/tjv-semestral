package cz.cvut.fit.tjv.hlavaj39.semestral.server.domain;

import java.time.LocalDate;
import java.util.Objects;

public class CScout {
    private int m_Id;
    private String m_Name;
    private LocalDate m_DateOfBirth;

    public CScout(int m_Id, String m_Name, LocalDate m_DateOfBirth) {
        this.m_Id = m_Id;
        this.m_Name = m_Name;
        this.m_DateOfBirth = m_DateOfBirth;
    }

    public CScout(int m_Id) {
        this.m_Id = m_Id;
        this.m_Name = null;
        this.m_DateOfBirth = null;
    }

    public int getM_Id() {
        return m_Id;
    }

    public void setM_Id(int m_Id) {
        this.m_Id = m_Id;
    }

    public String getM_Name() {
        return m_Name;
    }

    public void setM_Name(String m_Name) {
        this.m_Name = m_Name;
    }

    public LocalDate getM_DateOfBirth() {
        return m_DateOfBirth;
    }

    public void setM_DateOfBirth(LocalDate m_DateOfBirth) {
        this.m_DateOfBirth = m_DateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CScout cScout = (CScout) o;
        return m_Id == cScout.m_Id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_Id);
    }
}
