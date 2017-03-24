/*
 * The MIT License (MIT)
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.despector.ast.members.insn.branch;

import static com.google.common.base.Preconditions.checkNotNull;

import org.spongepowered.despector.ast.members.insn.InstructionVisitor;
import org.spongepowered.despector.ast.members.insn.Statement;
import org.spongepowered.despector.ast.members.insn.StatementBlock;
import org.spongepowered.despector.ast.members.insn.branch.Break.Breakable;
import org.spongepowered.despector.ast.members.insn.branch.condition.Condition;

import java.util.ArrayList;
import java.util.List;

/**
 * A while loop.
 */
public class While implements Statement, Breakable {

    private Condition condition;
    private StatementBlock body;
    private List<Break> breaks = new ArrayList<>();

    public While(Condition condition, StatementBlock body) {
        this.condition = checkNotNull(condition, "condition");
        this.body = checkNotNull(body, "body");
    }

    /**
     * Gets the condition of the loop.
     */
    public Condition getCondition() {
        return this.condition;
    }

    /**
     * Sets the condition of the loop.
     */
    public void setCondition(Condition condition) {
        this.condition = checkNotNull(condition, "condition");
    }

    /**
     * Gets the body of the loop.
     */
    public StatementBlock getBody() {
        return this.body;
    }

    /**
     * Sets the body of the loop.
     */
    public void setBody(StatementBlock block) {
        this.body = checkNotNull(block, "body");
    }

    @Override
    public List<Break> getBreaks() {
        return this.breaks;
    }

    @Override
    public void accept(InstructionVisitor visitor) {
        visitor.visitWhileLoop(this);
        this.condition.accept(visitor);
        for (Statement stmt : this.body.getStatements()) {
            stmt.accept(visitor);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("while (");
        sb.append(this.condition);
        sb.append(") {\n");
        for (Statement insn : this.body.getStatements()) {
            sb.append("    ").append(insn).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof While)) {
            return false;
        }
        While insn = (While) obj;
        return this.condition.equals(insn.condition) && this.body.equals(insn.body);
    }

}
