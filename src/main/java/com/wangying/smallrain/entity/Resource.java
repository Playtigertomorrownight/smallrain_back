package com.wangying.smallrain.entity;

import java.util.Date;

import com.wangying.smallrain.entity.enums.FileDataType;

public class Resource {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resource.id
     *
     * @mbggenerated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resource.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resource.type
     *
     * @mbggenerated
     */
    private FileDataType type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resource.label
     *
     * @mbggenerated
     */
    private String label;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resource.size
     *
     * @mbggenerated
     */
    private Long size;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resource.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resource.path
     *
     * @mbggenerated
     */
    private String path;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resource.description
     *
     * @mbggenerated
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resource.user_id
     *
     * @mbggenerated
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resource.image
     *
     * @mbggenerated
     */
    private String image;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resource.suffix
     *
     * @mbggenerated
     */
    private String suffix;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resource.id
     *
     * @return the value of resource.id
     *
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resource.id
     *
     * @param id the value for resource.id
     *
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resource.name
     *
     * @return the value of resource.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resource.name
     *
     * @param name the value for resource.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resource.type
     *
     * @return the value of resource.type
     *
     * @mbggenerated
     */
    public FileDataType getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resource.type
     *
     * @param type the value for resource.type
     *
     * @mbggenerated
     */
    public void setType(FileDataType type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resource.label
     *
     * @return the value of resource.label
     *
     * @mbggenerated
     */
    public String getLabel() {
        return label;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resource.label
     *
     * @param label the value for resource.label
     *
     * @mbggenerated
     */
    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resource.size
     *
     * @return the value of resource.size
     *
     * @mbggenerated
     */
    public Long getSize() {
        return size;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resource.size
     *
     * @param size the value for resource.size
     *
     * @mbggenerated
     */
    public void setSize(Long size) {
        this.size = size;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resource.create_time
     *
     * @return the value of resource.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resource.create_time
     *
     * @param createTime the value for resource.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resource.path
     *
     * @return the value of resource.path
     *
     * @mbggenerated
     */
    public String getPath() {
        return path;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resource.path
     *
     * @param path the value for resource.path
     *
     * @mbggenerated
     */
    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resource.description
     *
     * @return the value of resource.description
     *
     * @mbggenerated
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resource.description
     *
     * @param description the value for resource.description
     *
     * @mbggenerated
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resource.user_id
     *
     * @return the value of resource.user_id
     *
     * @mbggenerated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resource.user_id
     *
     * @param userId the value for resource.user_id
     *
     * @mbggenerated
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resource.image
     *
     * @return the value of resource.image
     *
     * @mbggenerated
     */
    public String getImage() {
        return image;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resource.image
     *
     * @param image the value for resource.image
     *
     * @mbggenerated
     */
    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resource.suffix
     *
     * @return the value of resource.suffix
     *
     * @mbggenerated
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resource.suffix
     *
     * @param suffix the value for resource.suffix
     *
     * @mbggenerated
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix == null ? null : suffix.trim();
    }
}