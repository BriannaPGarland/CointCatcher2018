//Again this is a class that we are using in the code we are turning in for our final project and not submitting this code 
//as our own. We got it from : https://www.warrenhills.org/Page/4695 and Brianna's High school teacher emailed her the Solid Object class 




import java.awt.*;
import java.util.*;
public class SolidObject // Applys to all objex who need collision detection
{
     public Rectangle collisionHull; // Rectangle teste
     public double posX,posY; // Position
     public int sizeX,sizeY; // Size of collision HULL
     public void makeSolidObject(int pos_x,int pos_y,int size_x,int size_y) // Create new solid object
     {
          posX = pos_x;
          posY = pos_y;
          sizeX = size_x;
          sizeY = size_y;
     }
     public void compileHull()
     {
          // Compiles the hull into a Rectangle class for colliding
          collisionHull = new Rectangle((int)posX,(int)posY,sizeX,sizeY);
     }
     public boolean isCollidingWith(SolidObject what)
     {
          // Test whether the collision between two solid objex is happening
          compileHull();
          what.compileHull();
          if(collisionHull.intersects(what.collisionHull))
               return true;
          return false;
     }
     public boolean isCollidingWith(Rectangle hull,SolidObject what)
     {
          // Same, but with raw rectangles
          what.compileHull();
          if(hull.intersects(what.collisionHull))
               return true;
          return false;
     }
     public boolean isColliding(ArrayList solidObjects)
     {
          // Test whether the collision between this object and any one in the list of objex is happening
          for(int index = 0;index < solidObjects.size();index++)
          {
               if(isCollidingWith((SolidObject)solidObjects.get(index)))
               {
                    return true;
               }
          }
          return false;
     }
     public void transport(double pos_x,double pos_y)
     {
          // Teleport this object to the location given
          posX = pos_x;
          posY = pos_y;
     }
     public void move(double pos_x,double pos_y)
     {
          // Move this object by the amount given ignoring collision detection
          posX += pos_x;
          posY += pos_y;
     }
     public void move(double pos_x,double pos_y,ArrayList solidObjects)
     {
          // Move this object by the amount given with collision detection
          posX += pos_x;
          posY += pos_y;
          if(isColliding(solidObjects))
          {
               posX -= pos_x;
               posY -= pos_y;
          }
     }
     public int getDirectionToSlideIn(SolidObject what,int direction)
     {
          // Find out what direction to slide in. Returns zero if nothing was hit.
          Rectangle a,b;
          switch(direction)
          {
               case 3: // MOVE DOWN
               case 1: // MOVE UP
               a = new Rectangle((int)posX,(int)posY,sizeX/2,sizeY);
               b = new Rectangle((int)(posX+sizeX/2),(int)posY,sizeX/2,sizeY);
               if(!(isCollidingWith(a,what) && isCollidingWith(b,what)))
               {
                    if(isCollidingWith(a,what))
                         return 2;
                    if(isCollidingWith(b,what))
                         return 4;
               }
               case 2: // MOVE RIGHT
               case 4: // MOVE LEFT
               a = new Rectangle((int)posX,(int)posY,sizeX,sizeY/2);
               b = new Rectangle((int)posX,(int)(posY+sizeY/2),sizeX,sizeY/2);
               if(!(isCollidingWith(a,what) && isCollidingWith(b,what)))
               {
                    if(isCollidingWith(a,what))
                         return 3;
                    if(isCollidingWith(b,what))
                         return 1;
               }     
          }
          return 0;
     }
     public void moveSmart(int direction,double length,ArrayList solidObjects,int num_moves)
     {
          // Move this object, and slide it along the corners
          if(num_moves < 2)
          {
               double pos_x = 0,pos_y = 0;
               switch(direction)
               {
                    case 1:
                    pos_x = 0;
                    pos_y = -length;
                    break;
                    case 2:
                    pos_x = length;
                    pos_y = 0;
                    break;
                    case 3:
                    pos_x = 0;
                    pos_y = length;
                    break;
                    case 4:
                    pos_x = -length;
                    pos_y = 0;
                    break;
               }
               posX += pos_x;
               posY += pos_y; 
               for(int index = 0;index < solidObjects.size();index++)
               {
                    if(isCollidingWith((SolidObject)solidObjects.get(index)))
                    {
                         int movedir = getDirectionToSlideIn((SolidObject)solidObjects.get(index),direction);
                         posX -= pos_x;
                         posY -= pos_y;
                         moveSmart(movedir,length,solidObjects,num_moves+1);
                    }
               }
          }
     }
}
