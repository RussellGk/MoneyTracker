package com.hardteam.moneytracker.rest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RG on 24.01.2016.
 */
public class CreateExpense {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("sum")
    @Expose
    private Double sum;
    @SerializedName("tr_date")
    @Expose
    private String trDate;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The categoryId
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     *
     * @param categoryId
     * The category_id
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     *
     * @return
     * The comment
     */
    public String getComment() {
        return comment;
    }

    /**
     *
     * @param comment
     * The comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     *
     * @return
     * The sum
     */
    public Double getSum() {
        return sum;
    }

    /**
     *
     * @param sum
     * The sum
     */
    public void setSum(Double sum) {
        this.sum = sum;
    }

    /**
     *
     * @return
     * The trDate
     */
    public String getTrDate() {
        return trDate;
    }

    /**
     *
     * @param trDate
     * The tr_date
     */
    public void setTrDate(String trDate) {
        this.trDate = trDate;
    }
}
