package org.graphstream.gama.extension.receiver;

import msi.gama.common.interfaces.IKeyword;
import msi.gama.precompiler.ISymbolKind;
import msi.gama.precompiler.GamlAnnotations.facet;
import msi.gama.precompiler.GamlAnnotations.facets;
import msi.gama.precompiler.GamlAnnotations.inside;
import msi.gama.precompiler.GamlAnnotations.symbol;
import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gaml.descriptions.IDescription;
import msi.gaml.expressions.IExpression;
import msi.gaml.statements.AbstractStatement;
import msi.gaml.statements.IStatement;
import msi.gaml.types.IType;

import org.graphstream.gama.extension.GSManager;
import org.graphstream.gama.extension.IKeywordGSAdditional;


@symbol(name = IKeywordGSAdditional.ADD_EDGE_ATTRIBUTE, kind = ISymbolKind.SINGLE_STATEMENT, with_sequence = false)
@inside(kinds = { ISymbolKind.BEHAVIOR, ISymbolKind.SINGLE_STATEMENT })
@facets(value = { @facet(name = IKeywordGSAdditional.RECEIVERID, type = IType.STRING, optional = false),
		 @facet(name = IKeywordGSAdditional.EDGE_ID, type = IType.STRING, optional = false),
		 @facet(name = IKeywordGSAdditional.ATTRIBUTE_NAME, type = IType.STRING, optional = false),
		 @facet(name = IKeyword.RETURNS, type = IType.NEW_TEMP_ID, optional = false)})
public class GetEdgeAttributeStatement extends AbstractStatement implements IStatement{
	
	final IExpression receiverid;
	final IExpression edgeid;
	final IExpression attname;
	final String returns;
	
	public GetEdgeAttributeStatement(IDescription desc) {
		super(desc);
		receiverid = getFacet(IKeywordGSAdditional.RECEIVERID);
		edgeid = getFacet(IKeywordGSAdditional.EDGE_ID);
		attname = getFacet(IKeywordGSAdditional.ATTRIBUTE_NAME);
		returns = getLiteral(IKeyword.RETURNS);
	}

	@Override
	protected Object privateExecuteIn(IScope scope) throws GamaRuntimeException {
		String r = (String)(receiverid.value(scope));
		String eid = (String)(edgeid.value(scope));
		String an = (String)(attname.value(scope));
		GSReceiver receiver = GSManager.getReceiver(r);
		Object av = receiver.receiveEdgeAttribute(eid, an);
		// and we return the attribute list
		scope.setVarValue(returns, av);
		return av;
	}
}