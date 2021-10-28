package cz.cvut.fit.tjv.hlavaj39.semestral.server.domain;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

public class CUnit {
    private int m_Number;
    private String m_Name;
    private String m_Location;
    private Set<CScout> m_Members;


    public CUnit(int m_Number, String m_Name, String m_Location, Set<CScout> m_Members) {
        this.m_Number = m_Number;
        this.m_Name = m_Name;
        this.m_Location = m_Location;
        this.m_Members = m_Members;
    }

    public CUnit(int m_Number) {
        this.m_Number = m_Number;
        this.m_Name = null;
        this.m_Location = null;
        this.m_Members = null;
    }

    public int getM_Number() {
        return m_Number;
    }

    public void setM_Number(int m_Number) {
        this.m_Number = m_Number;
    }

    public String getM_Name() {
        return m_Name;
    }

    public void setM_Name(String m_Name) {
        this.m_Name = m_Name;
    }

    public String getM_Location() {
        return m_Location;
    }

    public void setM_Location(String m_Location) {
        this.m_Location = m_Location;
    }

    public Set<CScout> getM_Members() {
        return m_Members;
    }

    public void setM_Members(Set<CScout> m_Members) {
        this.m_Members = m_Members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CUnit cUnit = (CUnit) o;
        return m_Number == cUnit.m_Number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_Number);
    }
}
