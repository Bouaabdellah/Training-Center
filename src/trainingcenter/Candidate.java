/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trainingcenter;

import java.time.LocalDate;
import java.time.*;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author bouaa
 */
public class Candidate {

    // attributes
    private String _state;
    private training _training;
    private LocalDate _trainingBeginDate;
    private LocalDate _registrationDate;
    private String _status;
    private boolean _seniority;
    private float _discount;
    private double _duration;
    private double _totalPrice;
    private double _price;

    // constructor
    public Candidate(training training, LocalDate trainingBeginDate, LocalDate registrationDate, String status, boolean seniority) {
        _training = training;
        _trainingBeginDate = trainingBeginDate;
        _registrationDate = registrationDate;
        _status = status;
        _seniority = seniority;
    }

    // getters and setters
    public LocalDate getTrainingBeginDate() {
        return _trainingBeginDate;
    }

    public LocalDate getRegistrationDate() {
        return _registrationDate;
    }

    public String getStatus() {
        return _status;
    }

    public boolean getSeniority() {
        return _seniority;
    }

    public training getTrainging() {
        return _training;
    }

    public String getState() {
        return _state;
    }

    public void setState() {
        _state = getTrainingBeginDate().equals(getRegistrationDate()) ? "on time" : "late";
    }

    public float getDiscount() {
        return _discount;
    }

    public void setDiscount() {
        float discount = 0.0f;

        // depend on candidate status
        if (getStatus().equals("Student")) {
            discount += 20;
        } else if (getStatus().equals("Teacher")) {
            discount += 10;
        }

        // depend on candidate seniority
        if (getSeniority()) {
            discount += 5;
        }

        _discount = discount;
    }

    public double getDuration() {
        return _duration;
    }

    public void setDuration() {
        _duration = getTrainging().duration();

        // remove added days if days are not equivalent
        if (!getTrainingBeginDate().isEqual(getRegistrationDate())) {
            double totalMonths = 0.0;
            LocalDate current = getTrainingBeginDate();

            while (current.isBefore(getRegistrationDate())) {
                YearMonth currentMonth = YearMonth.from(current);
                int daysInMonth = currentMonth.lengthOfMonth();

                LocalDate endOfCurrentMonth = currentMonth.atEndOfMonth().plusDays(1); // start of next month
                LocalDate next = getRegistrationDate().isBefore(endOfCurrentMonth) ? getRegistrationDate() : endOfCurrentMonth;

                long daysBetween = ChronoUnit.DAYS.between(current, next);
                totalMonths += (double) daysBetween / daysInMonth;

                current = next;
            }
            double daysBetween = Math.round(totalMonths * 100.0) / 100.0;
            if (getTrainingBeginDate().getDayOfMonth() > getRegistrationDate().getDayOfMonth()) {
                _duration += daysBetween;
            } else {
                _duration -= daysBetween;
            }
        }
    }

    public double getPrice() {
        return _price;
    }

    public void setPrice() {
        _price = getTrainging().monthlyPrice() * getDuration();
        // substract discount
        _price -= Math.round(_price * getDiscount()) / 100.0;
    }
}
