package com;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.*;

@SuppressWarnings("serial")
public class LMSServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/html");

        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

        String btn = req.getParameter("btn");

        if (btn.equals("Add Member")) {
            String usn = req.getParameter("USN");
            String name = req.getParameter("Name");

            Query q = new Query("Member");

            PreparedQuery pq = ds.prepare(q);
            for (Entity result : pq.asIterable()) {

                String USN = result.getProperty("USN").toString();
                if (usn.equals(USN)) {

                    resp.getWriter().println("<center><H1>USN Already Exists</H1></center>");
                    return;
                }
            }

            Entity member = new Entity("Member", usn);
            member.setProperty("USN", usn);
            member.setProperty("Name", name);
            ds.put(member);
            // resp.getWriter().println("<center><H1>Member Added</H1></center>");

            resp.getWriter().println("<script type=\"text/javascript\">");
            resp.getWriter().println("alert('Member Added');");
            resp.getWriter().println("</script>");

            //RequestDispatcher rd = req.getRequestDispatcher("Home.jsp");
            //req.setAttribute("msg", "Member Added");
            //rd.forward(req, resp);

            resp.sendRedirect("/Home.jsp");
        }

        if (btn.equals("Add Book")) {
            String bookid = req.getParameter("BookId");
            String title = req.getParameter("Title");

            Query q = new Query("Book");

            PreparedQuery pq = ds.prepare(q);
            for (Entity result : pq.asIterable()) {

                String Bookid = result.getProperty("BookId").toString();
                if (Bookid.equals(bookid)) {

                    resp.getWriter().println("<center><H1>BookId Already Exists</H1></center>");
                    return;
                }
            }

            Entity book = new Entity("Book", bookid);
            book.setProperty("BookId", bookid);
            book.setProperty("Title", title);
            ds.put(book);
            resp.getWriter().println("<center><H1>Book Added</h1></center>");
        }

        if (btn.equals("Issue Book")) {
            String usn = req.getParameter("USN");
            String bookid = req.getParameter("BookId");

            int flag = 0;
            Query q = new Query("Member");
            PreparedQuery pq = ds.prepare(q);
            for (Entity result : pq.asIterable()) {

                String USN = result.getProperty("USN").toString();
                if (usn.equals(USN)) {
                    flag = 1;
                    break;
                }
            }
            if (flag == 0) {
                resp.getWriter().println("<center><H1>USN Doesnot Exists</h1></center>");
                return;
            }
            // USN exists, check if bookid exists
            else {
                q = new Query("Book");
                flag = 0;
                pq = ds.prepare(q);
                for (Entity result : pq.asIterable()) {

                    String Bookid = result.getProperty("BookId").toString();
                    if (Bookid.equals(bookid)) {
                        flag = 1;
                        break;
                    }
                }
            }
            if (flag == 0) {
                resp.getWriter().println("<center><H1>BookId Doesnot Exists</h1></center>");
                return;
            } else {

                Entity issue = new Entity("Issue", usn);
                issue.setProperty("USN", usn);
                issue.setProperty("BookId", bookid);
                ds.put(issue);
                resp.getWriter().println("<center><H1>Book Issued</h1></center>");
            }
        }

        if (btn.equals("Return Book")) {
            String usn = req.getParameter("USN");
            String bookid = req.getParameter("BookId");

            int flag = 0;
            Query q = new Query("Issue");
            PreparedQuery pq = ds.prepare(q);
            for (Entity result : pq.asIterable()) {

                String USN = result.getProperty("USN").toString();
                String BookId = result.getProperty("BookId").toString();
                if (usn.equals(USN) && (BookId.equals(bookid))) {
                    flag = 1;
                    break;
                }
            }
            if (flag == 0) {
                resp.getWriter().println("<center><H1>It seems book with id : " + bookid + " not issued to this USN : " + usn
                                             + "</h1></center>");
                return;
            } else {
                Entity issue = new Entity("Issue", usn);
                ds.delete(issue.getKey());
                resp.getWriter().println("<center><H1>Book Returned</h1></center>");
            }
        }

    }
}
