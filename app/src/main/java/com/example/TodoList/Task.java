package com.example.TodoList;

class Task {
   private int id =0;
   private String name;
   private String description;
   private String dateStart;
   private String dateFinish;
   private Long dateFinishLong = 9999999999990l;
   private String dateEnd;
   private int complete = 0;

   public Task(String name, String description, String dateFinish, String dateStart) {
      this.name = name;
      this.description = description;
      this.dateFinish = dateFinish;
      this.dateStart = dateStart;
   }
   public Long getDateFinishLong() {
      return dateFinishLong;
   }

   public void setDateFinishLong(Long dateFinishLong) {
      this.dateFinishLong = dateFinishLong;
   }
   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getDateStart() {
      return dateStart;
   }

   public void setDateStart(String dateStart) {
      this.dateStart = dateStart;
   }

   public String getDateFinish() {
      return dateFinish;
   }

   public void setDateFinish(String dateFinish) {
      this.dateFinish = dateFinish;
   }

   public String getDateEnd() {
      return dateEnd;
   }

   public void setDateEnd(String dateEnd) {
      this.dateEnd = dateEnd;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public int getComplete() {
      return complete;
   }

   public void setComplete(int complete) {
      this.complete = complete;
   }
}
