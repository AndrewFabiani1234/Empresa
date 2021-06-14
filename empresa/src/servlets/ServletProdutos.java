package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class ServletProdutos
 */
@WebServlet("/produtos")
public class ServletProdutos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletProdutos() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Se  não estiver logado, chama a ServeletUsuario
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/produtos/Listar.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String path = "WEB-INF/produtos/"; 
		String pagina = request.getParameter("pagina");
		boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
		if(isMultiPart) 
		{
			pagina = "incluir.jsp";
		}
		RequestDispatcher rd = request.getRequestDispatcher(path+pagina);
		rd.forward(request, response);
	}

}
