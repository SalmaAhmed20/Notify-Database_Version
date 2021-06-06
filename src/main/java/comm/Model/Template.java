package comm.Model;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Template {
      private  int id;
      private  String Subject;
      private String Content;
      private Languages supportedLanguages;
      private int numPlaceHolders;

    @Override
    public String toString() {
        return "Template{" +
                "id=" + id +
                ", Subject='" + Subject + '\'' +
                ", Content='" + Content + '\'' +
                ", supportedLanguages=" + supportedLanguages +
                ", numPlaceHolders=" + numPlaceHolders +
                '}'+'\n';
    }

    public Template( @JsonProperty("subject") String subject, @JsonProperty("contant") String content,
                     @JsonProperty("lang") Languages lang,
                     @JsonProperty("num") int numPlaceHolders) {
            this.Subject = subject;
            this.Content = content;
            this.setNumPlaceHolders(numPlaceHolders);
            this.supportedLanguages  =lang;
      }

      public int getID() {
            return id;
      }
      public String getSubject() {
            return Subject;
      }
      public String getContent() {
            return Content;
      }

      public Languages getSupportedLanguages() {
            return supportedLanguages;
      }

      public int getNumPlaceHolders() {
            return numPlaceHolders;
      }

      public void setContent( String content ) {
            Content = content;
      }

      public void setSubject( String subject ) {
            Subject = subject;
      }

      public void setNumPlaceHolders( int numPlaceHolders ) {
            if ( numPlaceHolders >= 1 )
                  this.numPlaceHolders = numPlaceHolders;
            else
                this.numPlaceHolders=1;
      }

      public void setSupportedLanguages( Languages supportedLanguages ) {
            this.supportedLanguages = supportedLanguages;
      }

      public  void setID( int id ) {
            this.id = id;
      }
}
