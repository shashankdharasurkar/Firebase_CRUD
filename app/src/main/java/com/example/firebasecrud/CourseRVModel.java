package com.example.firebasecrud;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class CourseRVModel implements Parcelable {
    String courseName;
    String coursePrice;
    String courseImg;
    String courseDescription;
    String courseLink;
    String courseBestSuitedFor;
    String courseId;

    public CourseRVModel() {


    }

    public CourseRVModel(String courseName, String coursePrice, String courseImg, String courseDescription, String courseLink, String courseBestSuitedFor, String courseId) {
        this.courseName = courseName;
        this.coursePrice = coursePrice;
        this.courseImg = courseImg;
        this.courseDescription = courseDescription;
        this.courseLink = courseLink;
        this.courseBestSuitedFor = courseBestSuitedFor;
        this.courseId = courseId;
    }

    protected CourseRVModel(Parcel in) {
        courseName = in.readString();
        coursePrice = in.readString();
        courseImg = in.readString();
        courseDescription = in.readString();
        courseLink = in.readString();
        courseBestSuitedFor = in.readString();
        courseId = in.readString();
    }

    public static final Creator<CourseRVModel> CREATOR = new Creator<CourseRVModel>() {
        @Override
        public CourseRVModel createFromParcel(Parcel in) {
            return new CourseRVModel(in);
        }

        @Override
        public CourseRVModel[] newArray(int size) {
            return new CourseRVModel[size];
        }
    };

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(String coursePrice) {
        this.coursePrice = coursePrice;
    }

    public String getCourseImg() {
        return courseImg;
    }

    public void setCourseImg(String courseImg) {
        this.courseImg = courseImg;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseLink() {
        return courseLink;
    }

    public void setCourseLink(String courseLink) {
        this.courseLink = courseLink;
    }

    public String getCourseBestSuitedFor() {
        return courseBestSuitedFor;
    }

    public void setCourseBestSuitedFor(String courseBestSuitedFor) {
        this.courseBestSuitedFor = courseBestSuitedFor;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(courseName);
        dest.writeString(coursePrice);
        dest.writeString(courseImg);
        dest.writeString(courseDescription);
        dest.writeString(courseLink);
        dest.writeString(courseBestSuitedFor);
        dest.writeString(courseId);
    }
}
