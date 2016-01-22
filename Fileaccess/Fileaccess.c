#include <stdio.h>
#include <sys/stat.h> //provides information about the system
#include <pwd.h> //provides information about the user
#include <string.h> //used by strcat

int main (int argc, char *argv[]) {
        int i = 1;

        while (argv[i] != NULL) {//argv[i] is the name of the file as a string.
                int flag1 = 0, flag2 = 0, flag3= 0;
                struct stat buf;
                char *f = argv[i];

                stat(f, &buf);

                struct passwd *ptr;
                ptr = getpwuid(getuid());

                if (buf.st_uid == getuid()) {//if st_uid and getuid are the same,
                //they have permission to access the file.
                        flag1 = 1;
                        flag2 = 1;
                }
                //compare read/write/execute permissions.
                if (((buf.st_mode & S_IRUSR) > 200) && ((buf.st_mode & S_IRUSR) < 300) &&
                        (buf.st_mode & S_IWUSR > 100) && (buf.st_mode & S_IXUSR == 0)) {
                        flag1 = 1;
                        flag2 = 1;
                        flag3 = 0;
                }
                if (((buf.st_mode & S_IWUSR) > 200) && ((buf.st_mode & S_IWUSR) < 200)) {
                        flag2 = 1;
                }
                if ((buf.st_mode & S_IXUSR) > 0) {
                        flag3 = 1;
                }

                //Check Group ids
                if (buf.st_gid == getgid()) {
                        flag1 = 1;
                        flag2 = 1;
                }

                //Check home directory of user.
                //Concatenate path to user's home directory.

                f = strcpy(argv[i], ptr->pw_dir);
                stat(f, &buf);

                //check permissions again with new f
                if ((buf.st_mode & S_IRUSR) < 99)  {
                        flag1 = 1;
                }
                if ((buf.st_mode & S_IWUSR) < 99) {
                        flag2 = 1;
                }
                if ((buf.st_mode & S_IXUSR) > 100) {
                        flag3 = 1;
                }


                //print according to flags
                printf("User's Permissions: -");
                if (flag1 == 1)
                        printf("r");
                else
                        printf("-");
                if (flag2 == 1)
                        printf("w");
                else
                        printf("-");
                if (flag3 == 1)
                        printf("x\t");
                else
                        printf("-\t");
                //print user name and file name
                printf("%s\t", ptr->pw_name);
                printf("%s\n", argv[i]);

                i++;
}


        return 0;
}
