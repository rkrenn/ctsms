/*
 * Copyright 2009-2011 Prime Technology.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.phoenixctms.ctsms.web.component.tagcloud;

import java.io.IOException;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import org.primefaces.renderkit.CoreRenderer;

@ResourceDependencies({
		@ResourceDependency(library = "primefaces", name = "primefaces.css"),
		@ResourceDependency(library = "css", name = "tagCloud.min.css"),
		@ResourceDependency(library = "primefaces", name = "jquery/jquery.js"),
		@ResourceDependency(library = "primefaces", name = "primefaces.js")
})
@FacesRenderer(componentFamily = "javax.faces.Output", rendererType = "ctsms.TagCloud")
public class TagCloudRenderer extends CoreRenderer {

	@Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		TagCloud tagCloud = (TagCloud) component;
		encodeMarkup(context, tagCloud);
		encodeScript(context, tagCloud);
	}

	protected void encodeMarkup(FacesContext context, TagCloud tagCloud) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		TagCloudModel model = tagCloud.getModel();
		String styleClass = tagCloud.getStyleClass();
		String style = tagCloud.getStyle();
		styleClass = styleClass == null ? TagCloud.STYLE_CLASS : TagCloud.STYLE_CLASS + " " + styleClass;
		writer.startElement("div", tagCloud);
		writer.writeAttribute("id", tagCloud.getClientId(context), "id");
		writer.writeAttribute("class", styleClass, "styleClass");
		if (style != null) {
			writer.writeAttribute("style", style, "style");
		}
		writer.startElement("ul", null);
		for (TagCloudItem item : model.getTags()) {
			writer.startElement("li", null);
			writer.writeAttribute("class", "ctsms-tagcloud-strength-" + item.getStrength(), null);
			String onclick = item.getOnclick();
			if (onclick != null && onclick.length() > 0) {
				writer.writeAttribute("onclick", onclick, null);
			}
			writer.writeText(item.getLabel(), null);
			writer.endElement("li");
		}
		writer.endElement("ul");
		writer.endElement("div");
	}

	protected void encodeScript(FacesContext context, TagCloud tagCloud) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		String clientId = tagCloud.getClientId(context);
		startScript(writer, clientId);
		writer.write("PrimeFaces.cw('TagCloud','" + tagCloud.resolveWidgetVar() + "',{");
		writer.write("id:'" + clientId + "'");
		writer.write("});");
		endScript(writer);
	}
}