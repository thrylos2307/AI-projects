#include <bits/stdc++.h>
using namespace std;

void FIT_VALUE(int a[],int n, int arr[4])
{
    int j,p,b,c,fit = 0;
      for(j=0;j<n;j++)
      {
          p =  abs(a[j]-arr[0]);
          b =  abs(a[j]-arr[1]);
          c =  abs(a[j]-arr[2]);

          p>b?(b>c?fit+=c:fit+=b):(p>c?fit+=c:fit+=p);
      }
      arr[3]=fit;
}

void CHROMOSOMES(int a[],int n,int arr[][4],int m)
{   /* chromosome is a array of length 3, where each block contains group representative of that group. group representative which is nearest to a element
      will contain that element in the group  */

    srand(time(0));
    int i,j,k;
    for(i=0;i<m;i++)
    {
        for(j=0;j<3;j++)
        {
            k = rand()%n;
            arr[i][j] = a[k];
        }
    }

     for(i=0;i<m;i++)
     {
         FIT_VALUE(a,n,arr[i]);
     }

}

void SEL_COUNT(int a[],int n,int arr[][4],int m)
{
    srand(time(0));
    int i,t,k,j,w;
    int arr1[m][4];
    for(i=0;i<m/2;i++)
    {   /* parent selection using tour. select */
        int maxi=0;
        for(t=0;t<20;t++)
           { w = rand()%m;
             if(arr[w][3]>maxi)
             {   maxi = arr[w][3];
                 k=w;}
           }
        maxi =0;
        for(t=0;t<20;t++)
           { w = rand()%m;
             if(arr[w][3]>maxi)
             {   maxi = arr[w][3];
                 j=w;}
           }

           /* parent selection using tournament selection .
                      parent are k and j                    */

           /* ************crossover***************/
         int c = rand()%3;

         for(t=0;t<=c;t++)
         {
             arr1[i][t] = arr[k][t];
             arr1[m-i-1][t] = arr[j][t];
         }
         for(t=c+1;t<=2;t++)
         {
             arr1[i][t] = arr[j][t];
             arr1[m-i-1][t] = arr[k][t];
         }
         /**crossover*****/
    }
     // new generation is creation
    /***mutating any y chromosomes from the new generation******/
    int y = rand()%m;


    for(i=0;i<y;i++)
    {
        int g,h,r;
        g = rand()%m;
        h = rand()%3;
        r = rand()%n;
        arr1[g][h] = a[r];
    }
    for(i=0;i<m;i++)
     {
         FIT_VALUE(a,n,arr1[i]);
     }

     for(i=0;i<m;i++)
     {
          for(t=0;t<4;t++)
          {
              arr[i][t]=arr1[i][t];
          }
     }

     /**************new generation in arr[][]*************************/

}

int main()
{
    srand(time(0));
    cout<<"Enter no. of students: "<<endl;
    int i,j,n;
    cin>>n;

    int a[n];
    for(i=0;i<n;i++)
     {
         a[i] = rand()%101+1;                // array of marks of n students
     }
    int pop[50][4];                    // population of 50 individual

    CHROMOSOMES(a,n,pop,50);

  /*   for(i=0;i<n;i++)
        cout<<a[i]<<" ";
     cout<<endl;

     for(i=0;i<50;i++)
    {  for(j=0;j<4;j++)
         {
        cout<<pop[i][j]<<" ";
         }
         cout<<endl;
    }
*/
    int gmi = 99999999;
    int gminimum[4];
    int itr = 2000;
    int mi = 99999999;
    while(itr-- || ((mi-gmi)>0) )
    {
    int minimum[4];

    for(i=0;i<50;i++)
    {
        if(pop[i][3] < mi)
        {
            mi = pop[i][3];
            minimum[0] = pop[i][0];
            minimum[1] = pop[i][1];
            minimum[2] = pop[i][2];
            minimum[3] = pop[i][3];
        }
    }

    if(mi<gmi)
    {
        gmi = mi;
        gminimum[0] = minimum[0];
        gminimum[1]=minimum[1];
        gminimum[2]=minimum[2];
        gminimum[3]=minimum[3];
    }

     SEL_COUNT(a,n,pop,50);


     //cout<<itr<<" : "<<endl;
     //cout<<mi<<endl;

    }


    vector <pair<int,int>> g1,g2,g3;
     int p,b,c;
      for(j=0;j<n;j++)
      {
          p =  abs(a[j]-gminimum[0]);
          b =  abs(a[j]-gminimum[1]);
          c =  abs(a[j]-gminimum[2]);

         // p>b?(b>c?(cout<<"element "<<j<<": "<<a[j]<<" in group-3"<<endl):(cout<<"element "<<j<<": "<<a[j]<<" in group-2"<<endl)):(p>c?(cout<<"element "<<j<<": "<<a[j]<<" in group-3"<<endl):(cout<<"element "<<j<<": "<<a[j]<<" in group-1"<<endl));
          p>b?(b>c?(g3.push_back(make_pair(a[j],j))):(g2.push_back(make_pair(a[j],j)))):(p>c?(g3.push_back(make_pair(a[j],j))):(g1.push_back(make_pair(a[j],j))));

      }


    cout<<"--group 1:"<<gminimum[0]<<" ||  group 2:"<<gminimum[1]<<" ||   group 3 : "<<gminimum[2]<<"\nfitness : "<<gminimum[3]<<" "<<"---"<<endl;
    cout<<"GROUP-1:---"<<endl;
    for(j=0;j<g1.size();j++)
    {
        cout<<"student  "<<g1[j].second<<" ---:--- "<<g1[j].first<<endl;
    }
    cout<<"GROUP-2:----"<<endl;
    for(j=0;j<g2.size();j++)
    {
        cout<<"student  "<<g2[j].second<<" ---:--- "<<g2[j].first<<endl;
    }
    cout<<"GROUP-3:---"<<endl;
    for(j=0;j<g3.size();j++)
    {
        cout<<"student  "<<g3[j].second<<" ---:--- "<<g3[j].first<<endl;
    }


   // cout<<g1.size()<<g2.size()<<g3.size()<<endl;




    return 0;
}